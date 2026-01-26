import { useState } from 'react'
import '../styles/ReviewsComponent.css'

export const ReviewsComponent = ({ reviews }) => {
  const [expanded, setExpanded] = useState(false)

  const visibleReviews = expanded ? reviews : reviews.slice(0, 3)

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

  return (
    <div
      className={`reviews-container ${expanded ? "expanded" : ""}`}
      onClick={() => setExpanded(!expanded)}
    >
      {visibleReviews.map((review, index) => {
        const stars = renderStars(review.stars)

        return (
          <div
            key={index}
            className="review-card"
          >
            <div className="review-header">
              <span className="review-user">{review.name}</span>
              <span className="review-date">
                {formatDate(review.creationDate)}
              </span>
            </div>

            <div className="stars">
              {stars.map((type, i) => (
                <span key={i} className={`star ${type}`} />
              ))}
            </div>

            <p className="review-text">{review.review}</p>
          </div>
        )
      })}

      {!expanded && <div className="fade-overlay" />}
    </div>
  )
}
