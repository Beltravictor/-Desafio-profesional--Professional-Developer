import { useContext, useState } from 'react'
import { UsuarioContext } from './UsuarioContext'
import { AuthContext } from './AuthProvider'

export const UsuarioProvider = ({ children }) => {

    const { logout } = useContext(AuthContext)

    const [token, setToken] = useState()

    const registrarUsuario = async (user) => {
        try {
            const res = await fetch("http://localhost:8081/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            })
            const data = await res.json()
            setToken(data)
        } catch (error) {
            // if (error.response.status === 409) {
            //     setToken({ token: "", message: "Ya existe una cuenta con ese email" })
            // }
            // if (error.response.status === 500) {
            //     setToken({ token: "", message: "Error del servidor" })
            // }
            setToken({ token: "", message: "Ya existe una cuenta con ese email" })
        }
    }

    const iniciarSesionUsuario = async (user) => {
        try {
            const res = await fetch("http://localhost:8081/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            })
            const data = await res.json()
            setToken(data)
        } catch (error) {
            // if (error.response.status === 404) {
            //     setToken({ token: "", message: "Usuario no encontrado" })
            // }
            // if (error.response.status === 500) {
            //     setToken({ token: "", message: "Error del servidor" })
            // }
            setToken({ token: "", message: "Email o contrseña incorrectos" })
        }
    }

    const [profile, setProfile] = useState({})

    const getProfile = async (token) => {
        try {
            const res = await fetch("http://localhost:8081/myuser/profile", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setProfile(data)
        } catch (error) {
            logout()
            setToken(null)
            setProfile({})
            console.log(error)
        }
    }

    const addFavorite = async (token, id) => {
        try {
            const res = await fetch(`http://localhost:8081/myuser/addfavorite/${id}`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
        } catch (error) {
            console.log(error)
        }
    }

    const removeFavorite = async (token, id) => {
        try {
            const res = await fetch(`http://localhost:8081/myuser/removefavorite/${id}`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
        } catch (error) {
            console.log(error)
        }
    }

    const eliminarMiUsuario = async (token) => {
        try {
            const res = await fetch("http://localhost:8081/auth/deletemyuser", {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
        } catch (error) {
            console.log(error)
        }
    }

    const [usuarios, setUsuarios] = useState([])

    const getUsuarios = async (token) => {
        try {
            const res = await fetch("http://localhost:8081/auth/users", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setUsuarios(data)
        } catch (error) {
            console.log(error)
        }
    }

    const eliminarUsuario = async (id, token) => {
        try {
            const res = await fetch(`http://localhost:8081/auth/users/${id}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            })
            getUsuarios(token)
        } catch (error) {
            console.log(error)
        }
    }

    const editarUsuario = async (newUser, token) => {
        try {
            const res = await fetch("http://localhost:8081/auth/users", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(newUser)
            })
            getUsuarios(token)
            const data = await res.text()
        } catch (error) {
            console.error("Error al Actualizar:", error)
        }
    }

    return (
        <UsuarioContext value={{
            token, setToken, registrarUsuario, iniciarSesionUsuario,
            profile, setProfile, getProfile, eliminarMiUsuario,
            usuarios, setUsuarios, getUsuarios, eliminarUsuario, editarUsuario,
            addFavorite, removeFavorite
        }}>
            {children}
        </UsuarioContext>

    )
}
