import React, { useEffect, useState } from "react"

export const CompartirComponent = ({ destino, url, id, setCompartir }) => {
  const [message, setMessage] = useState(
    `Estoy viendo este destino ${destino.name} y es espectacular ✈️🌍\nReserva tu viaje en ${url}`
  )

  const [networks, setNetworks] = useState({
    facebook: false,
    twitter: false,
    instagram: false,
  })

  useEffect(() => {
    document.title = `${destino.name} - Viajes increíbles`

    setMeta("og:title", destino.name)
    setMeta("og:description", destino.description)
    setMeta("og:image", destino.images[0])
    setMeta("og:url", url)
    setMeta("og:type", "website")

    setMeta("twitter:card", "summary_large_image", "name")
    setMeta("twitter:title", destino.name, "name")
    setMeta("twitter:description", destino.description, "name")
    setMeta("twitter:image", destino.images[0], "name")
  }, [destino, url])

  const setMeta = (key, content, attr = "property") => {
    let meta = document.querySelector(`meta[${attr}="${key}"]`)
    if (!meta) {
      meta = document.createElement("meta")
      meta.setAttribute(attr, key)
      document.head.appendChild(meta)
    }
    meta.setAttribute("content", content)
  }

  const toggleNetwork = (name) => {
    setNetworks((prev) => ({ ...prev, [name]: !prev[name] }))
  }

  const handleSubmit = () => {
    const selected = Object.keys(networks).filter((n) => networks[n])

    if (!message.trim() || selected.length === 0) {
      alert("Mensaje y al menos una red son obligatorios")
      return
    }

    if (networks.twitter) {
      window.open(
        `https://twitter.com/intent/tweet?text=${encodeURIComponent(
          message
        )}`,
        "_blank",
        "noopener,noreferrer"
      )
    }

    if (networks.facebook) {
      window.open(
        `https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(
          url
        )}`,
        "_blank",
        "noopener,noreferrer"
      )
    }

    if (networks.instagram) {
      alert(
        "Instagram no permite compartir por URL. Usá copiar texto o API móvil."
      )
    }
  }

  return (
    <div className="share-overlay">
      <div className="share-modal dark">
        <h2>Compartir en redes</h2>

        <textarea
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          maxLength={280}
        />

        <div className="char-counter">{message.length}/280</div>

        <div className="network-grid">
          <Network
            active={networks.facebook}
            label="Facebook"
            img="https://cdn-icons-png.flaticon.com/512/2175/2175193.png"
            onClick={() => toggleNetwork("facebook")}
          />
          <Network
            active={networks.twitter}
            label="X / Twitter"
            img="https://img.icons8.com/?size=256&id=A4DsujzAX4rw&format=png"
            onClick={() => toggleNetwork("twitter")}
          />
          <Network
            active={networks.instagram}
            label="Instagram"
            img="https://icons.veryicon.com/png/o/miscellaneous/offerino-icons/instagram-53.png"
            onClick={() => toggleNetwork("instagram")}
          />
        </div>

        <div className="actions">
          <button className="cancel" onClick={() => setCompartir(false)}>
            Cancelar
          </button>
          <button className="submit" onClick={handleSubmit}>
            Enviar
          </button>
        </div>
      </div>
    </div>
  )
}

const Network = ({ active, label, img, onClick }) => (
  <div className={`network-card ${active ? "active" : ""}`} onClick={onClick}>
    <img src={img} alt={label} />
    <span>{label}</span>
  </div>
)
