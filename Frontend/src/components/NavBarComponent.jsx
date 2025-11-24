import { NavLink } from 'react-router-dom'
import '../styles/NavBar.css'
import { useState } from 'react'

export const NavBarComponent = () => {

    const [openMenu, setOpenMenu] = useState(false)

    return (
        <nav className="navbar">

            <div className="logo-area">
                <NavLink to='/' className='logo myLink'>
                    <img src="src/assets/navbar/VuelosDH-icon.png" alt="VuelosDH-icon" />
                </NavLink>
                <NavLink to=    '/' className='logo myLink'>
                    <span className="lema">Viajá más, viví mejor</span>
                </NavLink>
            </div>

            <ul className="center-menu">
                <li><NavLink className="myLink" to='/'>Vuelos</NavLink></li>
                <li><NavLink className="myLink" to='/destinos'>Destinos</NavLink></li>
                <li><NavLink className="myLink" to='/administracion'>Administración</NavLink></li>
            </ul>

            <div className="registro">
                <button>Crear Cuenta</button>
                <button>Iniciar Sesión</button>
            </div>

            <button className="hamburger" onClick={() => setOpenMenu(!openMenu)}>
                ☰
            </button>

            <div className={`menu-panel ${openMenu ? 'open' : ''}`}>
                <ul>
                    <li><NavLink className="myLink" to='/'>Vuelos</NavLink></li>
                    <li><NavLink className="myLink" to='/destinos'>Destinos</NavLink></li>
                    <li><NavLink className="myLink" to='/administracion'>Administración</NavLink></li>
                </ul>

                <div className="registro-mobile">
                    <button>Crear Cuenta</button>
                    <button>Iniciar Sesión</button>
                </div>
            </div>

        </nav>
    )
}
