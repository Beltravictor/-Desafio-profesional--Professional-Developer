import { useState } from 'react'
import { NavLink } from 'react-router-dom'

export const DestinoFavoritoComponent = ({ destino, addFavorite, removeFavorite, token }) => {

    const [isFavorite, setIsFavorite] = useState(true)

    const toggleFavorite = () => {
        if (isFavorite) {
            setIsFavorite(false)
            removeFavorite(token, destino.id)
        } else {
            setIsFavorite(true)
            addFavorite(token, destino.id)
        }
    }

    return (
        <div className={`favorito-item ${open ? "open" : ""}`}>
            <button
                className={`favorite-button des-favorite ${isFavorite ? "active" : ""}`}
                onClick={() => toggleFavorite()}
                aria-label="Marcar como favorito"
            >
                <img
                    src={!isFavorite ? new URL(`../assets/destinos/fav-icon-unactive.png`, import.meta.url).href :
                        new URL(`../assets/destinos/fav-icon-active.png`, import.meta.url).href}
                    alt={isFavorite ? "Quitar de favoritos" : "Marcar como favorito"}
                    className="heart-img"
                />
            </button>

            <NavLink className="myLink" to={`/destinoinfo/${destino.id}`}>
                <div className="favorito-content">
                    <h3 className="favorito-title">{destino.name}</h3>

                    <p className="favorito-description">
                        {destino.description}
                    </p>
                </div>
            </NavLink>

            <NavLink className="myLink" to={`/destinoinfo/${destino.id}`}>
                <div className="favorito-image-wrapper">
                    <img
                        src={destino.images[0]}
                        alt={destino.name}
                        className="favorito-image"
                    />
                </div>
            </NavLink>

        </div>
    )
}
