import { NavLink } from 'react-router-dom'
import '../styles/NavBar.css'
import { useContext, useState } from 'react'
import { AuthContext } from '../context/Usuario/AuthProvider'
import { UsuarioContext } from '../context/Usuario/UsuarioContext'
import { AvatarPerfilComponent } from './AvatarPerfilComponent'
import logo from "../assets/navbar/VuelosDH-icon.png"

export const NavBarComponent = () => {

    const [openMenu, setOpenMenu] = useState(false)
    const [proflileMenu, setProfileMenu] = useState(false)
    const { user, logout } = useContext(AuthContext)
    const { setToken, setProfile } = useContext(UsuarioContext)

    const cerrarSesion = () => {
        logout()
        setToken(null)
        setProfile({})
        setProfileMenu(false)
    }

    const toggleProfileMenu = () => {
        if (proflileMenu)
            setProfileMenu(false)
        else
            setProfileMenu(true)
    }

    return (
        <nav className="navbar">

            <div className="logo-area">
                <NavLink to='/' className='logo myLink' onClick={() => window.scrollTo(0, 0)}>
                    <img src={logo} alt="VuelosDH-icon" />
                </NavLink>
                <NavLink to='/' className='logo myLink'>
                    <span className="lema">Viajá más, viví mejor</span>
                </NavLink>
            </div>

            <ul className="center-menu">
                <li><NavLink className="myLink" to='/'>Vuelos</NavLink></li>
                <li><NavLink className="myLink" to='/reservas'>Reservas</NavLink></li>
                <li><NavLink className="myLink" to='/destinos'>Destinos</NavLink></li>
                {user?.role === 'ROLE_ADMIN' &&
                    <li><NavLink className="myLink" to='/administracion'>Administración</NavLink></li>
                }
            </ul>

            {user ? (
                <div className="registro">
                    <h2>Bienvenido,<br /> {user.name}!</h2>
                    <AvatarPerfilComponent onClick={() => toggleProfileMenu()} className='nav-profile'/>
                    {proflileMenu && (
                        <div className="profile-menu">
                            <NavLink className="myLink" to='/perfil'>
                                <button onClick={() => setProfileMenu(false)} className='nav-btn'>Ver Perfil</button>
                            </NavLink>
                            <NavLink to='/' className='logo myLink'>
                                <button className='nav-btn' onClick={() => cerrarSesion()}>Cerrar Sesión</button>
                            </NavLink>
                        </div>
                    )}
                </div>
            ) : (
                <div className="registro">
                    <NavLink className="myLink" to='/registro'>
                        <button className='nav-btn'>Crear Cuenta</button>
                    </NavLink>
                    <NavLink className="myLink" to='/login'>
                        <div></div>
                        <button className='nav-btn'>Iniciar Sesión</button>
                    </NavLink>
                </div>)
            }

            <button className="hamburger" onClick={() => setOpenMenu(!openMenu)}>
                ☰
            </button>

            <div className={`menu-panel ${openMenu ? 'open' : ''}`}>

                {user ? (

                    <div className="registro-mobile">
                        <h2>Bienvenido, <br />{user.name}!</h2>
                        <NavLink className="myLink" to='/perfil'>
                            <button className='nav-btn'>Ver Perfil</button>
                        </NavLink>
                        <button className='nav-btn' onClick={() => cerrarSesion()}>Cerrar Sesión</button>
                    </div>
                ) : (
                    <div className="registro-mobile">
                        <NavLink className="myLink" to='/registro'>
                            <button className='nav-btn'>Crear Cuenta</button>
                        </NavLink>
                        <NavLink className="myLink" to='/login'>
                            <div></div>
                            <button className='nav-btn'>Iniciar Sesión</button>
                        </NavLink>
                    </div>)
                }

                <ul>
                    <li><NavLink className="myLink" to='/'>Vuelos</NavLink></li>
                    <li><NavLink className="myLink" to='/destinos'>Destinos</NavLink></li>
                    <li><NavLink className="myLink" to='/reservas'>Reservas</NavLink></li>
                    {user?.role === 'ROLE_ADMIN' &&
                        <li><NavLink className="myLink" to='/administracion'>Administración</NavLink></li>
                    }
                </ul>

            </div>

        </nav>
    )
}
