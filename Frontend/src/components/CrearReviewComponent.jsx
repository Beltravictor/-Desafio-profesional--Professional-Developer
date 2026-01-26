import { useContext, useState } from 'react'
import '../styles/CrearReviewComponent.css'
import { AuthContext } from '../context/Usuario/AuthProvider'
import { MyUserContext } from '../context/MyUser/MyUserContext'

export const CrearReviewComponent = ({ onClose, destination_id, actualizarReviews}) => {

    const { user } = useContext(AuthContext)
    const { crearReview } = useContext(MyUserContext)

    const [rating, setRating] = useState(0)
    const [hoverRating, setHoverRating] = useState(0)
    const [text, setText] = useState("")

    const handleSubmit = async () => {
        if (rating < 0.5 || text.trim() === "") return

        const review = {
            destination_id: destination_id,
            review: text,
            stars: rating
        }
        await crearReview(user.token, review)
        setText("")
        setRating(0)
        actualizarReviews()
        onClose(false)
    }

    const getStarType = (index) => {
        const value = index + 1
        const current = hoverRating || rating

        if (current >= value) return "full"
        if (current >= value - 0.5) return "half"
        return "empty"
    }

    return (
        <div className="review-overlay">
            <div className="review-modal">
                <h3>Puntua tu reseña</h3>

                <div className="star-selector">
                    {Array.from({ length: 5 }).map((_, index) => (
                        <div
                            key={index}
                            className={`star ${getStarType(index)}`}
                            onMouseMove={(e) => {
                                const rect = e.currentTarget.getBoundingClientRect()
                                const isHalf = e.clientX - rect.left < rect.width / 2
                                setHoverRating(index + (isHalf ? 0.5 : 1))
                            }}
                            onMouseLeave={() => setHoverRating(0)}
                            onClick={() => setRating(hoverRating)}
                        />
                    ))}
                </div>

                <textarea
                    placeholder="Contá tu experiencia..."
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                />

                <div className="actions">
                    <button className="cancel" onClick={() => onClose(false)}>
                        Cancelar
                    </button>
                    <button
                        className="submit"
                        onClick={handleSubmit}
                        disabled={rating < 0.5 || text.trim() === "" }
                    >
                        Publicar
                    </button>
                </div>
            </div>
        </div>
    )
}