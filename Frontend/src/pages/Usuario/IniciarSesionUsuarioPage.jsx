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

    const [usuarioError, setUsuarioError] = useState(false)
    const [usuarioErrorMensaje, setUsuarioErrorMensaje] = useState('')

    useEffect(() => {
        if (token) {
            if (token.token == '') {
                setUsuarioError(true)
                setUsuarioErrorMensaje(token.message)
                setToken(null)
            }
            else {
                const decoded = jwtDecode(token.token);
                login(decoded.firstname, decoded.lastname, decoded.role, decoded.sub, token.token)
                navigate('/')
            }
        }
    }, [token])

    const handleSubmit = (e) => {
        e.preventDefault()
        setUsuarioErrorMensaje('')
        const logUser = {
            email: email,
            password: password
        }
        iniciarSesionUsuario(logUser)
    }

    return (
        <div className="register-container">
            <form className="register-form" onSubmit={handleSubmit}>
                <h2>Iniciar Sesión</h2>
                {usuarioError &&
                    <span className="form-error">Error: {usuarioErrorMensaje}</span>
                }

                <div className="form-group">
                    <label>Email</label>
                    <input
                        type="text"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Contraseña</label>
                    <input
                        type="password"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                <button type="submit" className="btn-register">
                    Iniciar Sesión
                </button>
            </form>
            <NavLink className="myLink" to='/registro'>
                <button className="btn-sesion">
                    ¿No tenés una cuenta? Registrate!
                </button>
            </NavLink>
        </div>
    )
}
