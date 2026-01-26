import { NavLink } from 'react-router-dom';
import '../styles/Recomendaciones.css'
import { useContext, useEffect, useState } from "react"
import { DestinosContext } from '../context/Destinos/DestinosContext'

export const RecomendacionesComponent = ({ destino }) => {
  const { buscarReviewsPorDestino } = useContext(DestinosContext)

  const [reviews, setReviews] = useState([])

  useEffect(() => {
    if (destino) {
      const conseguirReviews = async () => {
        setReviews(await buscarReviewsPorDestino(destino.id))
      }
      conseguirReviews()
    }
  }, [destino])

  const promedioRating = () => {
    if (reviews.length == 0)
      return null
    const ratings = reviews.map(review => review.stars)
    const suma = ratings.reduce((acum, review) => acum + review)
    return suma / (reviews.length)
  }


  return (
    <NavLink className="myLink" to={`/destinoinfo/${destino.id}`}>
      <div className="tarjeta-horizontal">
        <div className="tarjeta-img-container">
          <img src={destino.images[0] ? destino.images[0] : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzuogXeCq49EOd9jFZi1ToymxGghiaemlCuQ&s"} alt={destino.name} className="tarjeta-img" />
          <div className="img-degrade"></div>

          <div className="tarjeta-texto">
            <p className='tarjeta-rating'>⭐{promedioRating()?.toFixed(1)}</p>
            <h3 className="tarjeta-nombre">{destino.name}</h3>
            <p className="tarjeta-precio">${Number(destino.sample_price).toLocaleString('es-AR')}</p>
            <p className="tarjeta-origen">Desde Buenos Aires</p>
            <p className="tarjeta-origen">{reviews.length} reseñas</p>
          </div>
        </div>

        {/* <div className="tarjeta-descripcion">
          <p>{destino.description}</p>
        </div> */}
      </div>
    </NavLink>
  )
}
