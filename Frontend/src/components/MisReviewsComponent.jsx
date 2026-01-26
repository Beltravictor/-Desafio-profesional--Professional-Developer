import { useContext, useEffect, useState } from 'react'
import { DestinosContext } from '../context/Destinos/DestinosContext'
import '../styles/ReviewsComponent.css'

export const MisReviewsComponent = ({ review, eliminarReview }) => {

  const { buscarDestinoPorId } = useContext(DestinosContext)

  const [name, setName] = useState('')

  useEffect(() => {
    const loadDestino = async () => {
      const destino = await buscarDestinoPorId(review.destination_id)
      setName(destino.name)
    }

    loadDestino()
  }, [review.destination_id])

  const renderStars = (rating) => {

    return Array.from({ length: 5 }, (_, index) => {
      const value = index + 1
      if (rating >= value) return "full"
      if (rating >= value - 0.5) return "half"
      return "empty"
    })
  }

  const formatDate = (date) => {
    return new Date(date).toLocaleDateString("es-AR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric"
    })
  }

  const stars = renderStars(review.stars)

  return (
    <div className="mi-review-card">
      <div className="review-header">
        <div>
          <span className="review-user">{name} </span>
          <span className="review-date">
            {formatDate(review.creationDate)}
          </span>
        </div>

        <button
          className="delete-review-btn"
          onClick={() => eliminarReview(review.id)}
        >
          ✕
        </button>
      </div>

      <div className="stars">
        {stars.map((type, i) => (
          <span key={i} className={`star ${type}`} />
        ))}
      </div>

      <p className="review-text">{review.review}</p>
    </div>
  )
}
