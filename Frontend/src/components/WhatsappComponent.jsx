import { useState } from 'react'
import '../styles/WhatsappComponent.css'

export const WhatsappComponent = () => {
  const phoneNumber = "5491123048533"
  const defaultMessage = "Hola, tengo una consulta sobre este destino"
  const [notification, setNotification] = useState(null)

  const handleWhatsAppClick = () => {
    if (!phoneNumber) {
      setNotification({
        type: "error",
        message: "No se pudo establecer comunicación. Número inválido."
      })
      return
    }

    try {
      const encodedMessage = encodeURIComponent(defaultMessage)
      const whatsappUrl = `https://wa.me/${phoneNumber}?text=${encodedMessage}`

      window.open(whatsappUrl, "_blank")

      setNotification({
        type: "success",
        message: "Redirigiendo a WhatsApp. Mensaje listo para enviar."
      })

      setTimeout(() => setNotification(null), 4000)
    } catch (error) {
      setNotification({
        type: "error",
        message: "Ocurrió un error al intentar abrir WhatsApp."
      })
    }
  }

  return (
    <>
      <button
        className="whatsapp-float"
        onClick={handleWhatsAppClick}
        aria-label="Contactar por WhatsApp"
      >
        <img
          src="https://upload.wikimedia.org/wikipedia/commons/6/6b/WhatsApp.svg"
          alt="WhatsApp"
        />
      </button>

      {notification && (
        <div className={`whatsapp-notification ${notification.type}`}>
          {notification.message}
        </div>
      )}
    </>
  )
}