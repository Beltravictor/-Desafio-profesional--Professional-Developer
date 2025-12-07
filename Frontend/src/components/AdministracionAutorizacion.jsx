import { NavLink } from 'react-router-dom'
import '../styles/AdministracionNoResponsive.css'

export const AdministracionAutorizacion = () => {
  return (
    <div className='no-auth'>
      <div className='no-auth-msg'>
        <p>Usted no tiene la autorización para administrar este sitio.</p>
        <NavLink className="myLink" to='/'>
        <button>Regresar a la página principal</button>
        </NavLink>
      </div>
    </div>
  )
}