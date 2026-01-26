import { useContext, useEffect, useState } from "react"
import "../../styles/RegistrarUsuarioPage.css"
import { UsuarioContext } from "../../context/Usuario/UsuarioContext"
import { NavLink, useNavigate } from "react-router-dom"
import { AuthContext } from "../../context/Usuario/AuthProvider"
import { jwtDecode } from "jwt-decode"

export const RegistrarUsuarioPage = () => {
    const { token, setToken, registrarUsuario } = useContext(UsuarioContext)
    const { login } = useContext(AuthContext)
    const navigate = useNavigate()

    const [name, setName] = useState('')
    const [lastname, setLastname] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const [errors, setErrors] = useState({})
    const [usuarioError, setUsuarioError] = useState(false)
    const [usuarioErrorMensaje, setUsuarioErrorMensaje] = useState('')

    useEffect(() => {
        if (token) {
            if (token.token === '') {
                setUsuarioError(true)
                setUsuarioErrorMensaje(token.message)
                setToken(null)
            } else {
                const decoded = jwtDecode(token.token)
                login(
                    decoded.firstname,
                    decoded.lastname,
                    decoded.role,
                    decoded.sub,
                    token.token
                )
                navigate('/')
            }
        }
    }, [token])

    const validateForm = () => {
        const newErrors = {}

        const emailRegex =
            /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/

        const passwordRegex =
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#._-])[A-Za-z\d@$!%*?&#._-]{8,}$/

        if (!name.trim()) {
            newErrors.name = 'El nombre es obligatorio'
        } else if (name.trim().length < 2) {
            newErrors.name = 'El nombre debe tener al menos 2 caracteres'
        }

        if (!lastname.trim()) {
            newErrors.lastname = 'El apellido es obligatorio'
        } else if (lastname.trim().length < 2) {
            newErrors.lastname = 'El apellido debe tener al menos 2 caracteres'
        }

        if (!email.trim()) {
            newErrors.email = 'El email es obligatorio'
        } else if (!emailRegex.test(email)) {
            newErrors.email = 'El email no tiene un formato válido'
        }

        if (!password.trim()) {
            newErrors.password = 'La contraseña es obligatoria'
        } else if (!passwordRegex.test(password)) {
            newErrors.password =
                'Mínimo 8 caracteres, 1 mayúscula, 1 minúscula, 1 número y 1 símbolo'
        }

        setErrors(newErrors)
        return Object.keys(newErrors).length === 0
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        setUsuarioError(false)
        setUsuarioErrorMensaje('')

        if (!validateForm()) return

        const newUser = {
            firstname: name,
            lastname: lastname,
            email: email,
            password: password
        }

        registrarUsuario(newUser)
    }

    return (
        <div className="register-container">
            <form
                className="register-form"
                onSubmit={handleSubmit}
                noValidate
            >
                <h2>Crear Cuenta</h2>

                {usuarioError && (
                    <span className="form-error">
                        Error: {usuarioErrorMensaje}
                    </span>
                )}

                <div className="form-group">
                    <label>Nombre</label>
                    <input
                        type="text"
                        name="firstname"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                    {errors.name && (
                        <span className="form-error">{errors.name}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Apellido</label>
                    <input
                        type="text"
                        name="lastname"
                        value={lastname}
                        onChange={(e) => setLastname(e.target.value)}
                    />
                    {errors.lastname && (
                        <span className="form-error">{errors.lastname}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Email</label>
                    <input
                        type="email"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    {errors.email && (
                        <span className="form-error">{errors.email}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Contraseña</label>
                    <input
                        type="password"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    {errors.password && (
                        <span className="form-error">{errors.password}</span>
                    )}
                </div>

                <button type="submit" className="btn-register">
                    Crear Cuenta
                </button>
            </form>

            <NavLink className="myLink" to="/login">
                <button className="btn-sesion">
                    ¿Ya tenés una cuenta? Inicia Sesión!
                </button>
            </NavLink>
        </div>
    )
}
