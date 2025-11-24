import { NavLink } from 'react-router-dom';
import '../styles/Recomendaciones.css'

export const RecomendacionesComponent = ({ destino }) => {
  
  return (
    <NavLink className="myLink" to={`/destinoinfo/${destino.id}`}>
      <div className="tarjeta-horizontal">
        <div className="tarjeta-img-container">
          <img src={destino.images[0] ? destino.images[0] : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzuogXeCq49EOd9jFZi1ToymxGghiaemlCuQ&s"} alt={destino.name} className="tarjeta-img" />
          <div className="img-degrade"></div>

          <div className="tarjeta-texto">
            <p className='tarjeta-rating'>⭐{destino.rating}</p>
            <h3 className="tarjeta-nombre">{destino.name}</h3>
            <p className="tarjeta-precio">${Number(destino.sample_price).toLocaleString('es-AR')}</p>
            <p className="tarjeta-origen">Desde Buenos Aires</p>
          </div>
        </div>

        {/* <div className="tarjeta-descripcion">
          <p>{destino.description}</p>
        </div> */}
      </div>
    </NavLink>
  )
}
