import { useContext, useEffect, useState } from "react";
import "../../styles/RegistrarUsuarioPage.css";
import { UsuarioContext } from "../../context/Usuario/UsuarioContext";
import { NavLink, useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/Usuario/AuthProvider";
import { jwtDecode } from "jwt-decode";

export const RegistrarUsuarioPage = () => {

  const { token, setToken, registrarUsuario } = useContext(UsuarioContext)
  const { login } = useContext(AuthContext)
  const navigate = useNavigate()

  const [name, setName] = useState('')
  const [lastname, setLastname] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const [emailError, setEmailError] = useState(false)
  const [passwordError, setPasswordError] = useState(false)
  const [usuarioError, setUsuarioError] = useState(false)
  const [usuarioErrorMensaje, setUsuarioErrorMensaje] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()

    let error = false
    setUsuarioError(false)
    setUsuarioErrorMensaje('')

    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#._-])[A-Za-z\d@$!%*?&#._-]{8,}$/

    if (!emailRegex.test(email)) {
      setEmailError(true)
      error = true
    } else
      setEmailError(false)

    if (!passwordRegex.test(password)) {
      setPasswordError(true)
      error = true
    } else
      setPasswordError(false)


    if (error == false) {
      const newUser = {
        firstname: name,
        lastname: lastname,
        email: email,
        password: password
      }
      registrarUsuario(newUser)
    }
  }

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


  return (
    <div className="register-container">
      <form className="register-form" onSubmit={handleSubmit}>
        <h2>Crear Cuenta</h2>
        {usuarioError &&
          <span className="form-error">Error: {usuarioErrorMensaje}</span>
        }

        <div className="form-group">
          <label>Nombre</label>
          <input
            type="text"
            name="firstname"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>Apellido</label>
          <input
            type="text"
            name="lastname"
            value={lastname}
            onChange={(e) => setLastname(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label>Email</label>
          <input
            type="text"
            name="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          {emailError &&
            <span className="form-error">Email inválido</span>
          }
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
          {passwordError &&
            <span className="form-error">Mínimo 8 caracteres,
              1 mayúscula, 1 minúscula, 1 número, 1 símbolo</span>
          }
        </div>

        <button type="submit" className="btn-register">
          Crear Cuenta
        </button>
      </form>
      <NavLink className="myLink" to='/login'>
        <button className="btn-sesion">
          ¿Ya tenés una cuenta? Inicia Sesión!
        </button>
      </NavLink>
    </div>
  )
}
