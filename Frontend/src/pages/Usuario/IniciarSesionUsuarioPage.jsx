import { useContext, useEffect, useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { UsuarioContext } from '../../context/Usuario/UsuarioContext'
import { AuthContext } from '../../context/Usuario/AuthProvider'
import { jwtDecode } from 'jwt-decode'

export const IniciarSesionUsuarioPage = () => {
    const { token, setToken, iniciarSesionUsuario } = useContext(UsuarioContext)
    const { login } = useContext(AuthContext)

    const navigate = useNavigate()

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

        if (!email.trim()) {
            newErrors.email = 'El email es obligatorio'
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            newErrors.email = 'El email no tiene un formato válido'
        }

        if (!password.trim()) {
            newErrors.password = 'La contraseña es obligatoria'
        } else if (password.length < 6) {
            newErrors.password = 'La contraseña debe tener al menos 6 caracteres'
        }

        setErrors(newErrors)
        return Object.keys(newErrors).length === 0
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        setUsuarioError(false)
        setUsuarioErrorMensaje('')

        if (!validateForm()) return

        const logUser = {
            email,
            password
        }

        iniciarSesionUsuario(logUser)
    }

    return (
        <div className="register-container">
            <form className="register-form" onSubmit={handleSubmit} noValidate>
                <h2>Iniciar Sesión</h2>

                {usuarioError && (
                    <span className="form-error">
                        Error: {usuarioErrorMensaje}
                    </span>
                )}

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
                    Iniciar Sesión
                </button>
            </form>

            <NavLink className="myLink" to="/registro">
                <button className="btn-sesion">
                    ¿No tenés una cuenta? Registrate!
                </button>
            </NavLink>
        </div>
    )
}
