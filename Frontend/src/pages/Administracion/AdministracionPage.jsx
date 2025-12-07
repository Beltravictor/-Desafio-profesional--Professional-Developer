import { NavLink } from 'react-router-dom'
import '../../styles/AdministracionPage.css'
import { AdministracionNoResponsive } from '../../components/AdministracionNoResponsive'
import { useContext } from 'react'
import { AdministracionAutorizacion } from '../../components/AdministracionAutorizacion'
import { AuthContext } from '../../context/Usuario/AuthProvider'

export const AdministracionPage = () => {

  const { user } = useContext(AuthContext)

  if (user?.role !== 'ROLE_ADMIN')
    return (<AdministracionAutorizacion />)

  return (
    <>
      <AdministracionNoResponsive />
      <div className="admin-container">
        <button className="admin-btn">Administrar Vuelos</button>
        <NavLink className="myLink" to={`/administracion/destinos`}>
          <button className="admin-btn">Administrar Destinos</button>
        </NavLink>
        <NavLink className="myLink" to={`/administracion/categorias`}>
          <button className="admin-btn">Administrar Categorías</button>
        </NavLink>
        <NavLink className="myLink" to={`/administracion/caracteristicas`}>
          <button className="admin-btn">Administrar Características</button>
        </NavLink>
        <NavLink className="myLink" to={`/administracion/usuarios`}>
          <button className="admin-btn">Administrar Usuarios</button>
        </NavLink>

      </div>
    </>
  )
}
