import { NavLink } from 'react-router-dom'
import '../../styles/AdministracionPage.css'
import { AdministracionNoResponsive } from '../../components/AdministracionNoResponsive'

export const AdministracionPage = () => {
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

      </div>   
    </>
  )
}
