import { useContext, useEffect, useState } from 'react'
import { DestinosContext } from '../../context/Destinos/DestinosContext'
import { CategoriasContext } from '../../context/Categorias/CategoriasContext'
import { NavLink, useNavigate, useParams } from 'react-router-dom'
import { HeaderComponent } from '../../components/HeaderComponent'
import { DestinoImagenesComponent } from '../../components/DestinoImagenesComponent'
import { CaracteristicasContext } from '../../context/Caracteristicas/CaracteristicasContext'
import "../../styles/DestinoInfoPage.css"
import { UsuarioContext } from '../../context/Usuario/UsuarioContext'
import { AuthContext } from '../../context/Usuario/AuthProvider'
import { CompartirComponent } from '../../components/CompartirComponent'
import { Calendar } from 'react-date-range'
import { VuelosContext } from '../../context/Vuelos/VuelosContext'
import { PoliticasComponent } from '../../components/PoliticasComponent'
import { ReviewsComponent } from '../../components/ReviewsComponent'
import { MyUserContext } from '../../context/MyUser/MyUserContext'

export const DestinoDetallePage = () => {
  const { id } = useParams()
  const navigate = useNavigate()

  const { user } = useContext(AuthContext)
  const { destinos, fetchDestinos, reviewsPorDestino, buscarReviewsPorDestino } = useContext(DestinosContext)
  const { categorias, fetchCategorias } = useContext(CategoriasContext)
  const { caracteristicas, fetchCaracteristicas } = useContext(CaracteristicasContext)
  const { misReservas, verMiReservas, } = useContext(MyUserContext)
  const { profile, getProfile, addFavorite, removeFavorite } = useContext(UsuarioContext)
  const { vuelos, vuelosPorOrigenDestino } = useContext(VuelosContext)

  const [isFavorite, setIsFavorite] = useState(false)
  const [compartir, setCompartir] = useState(false)
  const [origen, setOrigen] = useState(0)
  const [fechaIda, setFechaIda] = useState(new Date())
  const [favError, setFavError] = useState(false)


  useEffect(() => {
    fetchDestinos()
    fetchCaracteristicas()
    fetchCategorias()
    buscarReviewsPorDestino(id)
  }, [])

  useEffect(() => {
    if (user)
      getProfile(user.token)
  }, [user])

  useEffect(() => {
    if (user && profile) {
      verMiReservas(user.token)
    }
  }, [profile])

  useEffect(() => {
    if (Object.keys(profile).length !== 0) {
      if (profile.favorites.some(
        favId => Number(favId) == Number(id)))
        setIsFavorite(true)
      else
        setIsFavorite(false)
    }
  }, [profile])

  useEffect(() => {
    if (origen && origen != 0)
      vuelosPorOrigenDestino(origen, id)
  }, [origen])

  const toggleFavorite = () => {
    if (!user) {
      setFavError(true)
      return
    }
    if (isFavorite) {
      setIsFavorite(false)
      removeFavorite(user.token, id)
    } else {
      setIsFavorite(true)
      addFavorite(user.token, id)
    }
  }

  const normalizeToYMD = (value) => {
    const d = new Date(value)
    if (isNaN(d)) return null

    return `${d.getUTCFullYear()}-${d.getUTCMonth()}-${d.getUTCDate()}`
  }

  const disableAllExceptAllowedIda = (date) => {
    const allowedDates = new Set()
    vuelos.forEach((vuelo) => {
      allowedDates.add(normalizeToYMD(vuelo.departure_date))
    })
    return !allowedDates.has(normalizeToYMD(date))
  }

  const reservaDatesIda = (date) => {
    const startFlightIds = misReservas.map(r => r.startFlight)

    const misVuelos = vuelos.filter(v =>
      startFlightIds.includes(v.id)
    )

    const allowedDates = new Set(
      misVuelos.map(v => normalizeToYMD(v.departure_date))
    )
    const isMarked = allowedDates.has(normalizeToYMD(date))

    return (
      <span className={isMarked ? 'fecha-marcada' : ''}>
        {date.getDate()}
      </span>
    )
  }

  const promedioRating = () => {
    if (reviewsPorDestino.length == 0)
      return null
    const ratings = reviewsPorDestino.map(review => review.stars)
    const suma = ratings.reduce((acum, review) => acum + review)
    return suma / (reviewsPorDestino.length)
  }


  if (destinos.length == 0 || categorias.length == 0 || !destinos.find(des => des.id == id)) {
    return <div className="form-info form-item">
      <label>Cargando destino...</label>
    </div>
  }

  return (
    <>
      <HeaderComponent name={destinos.find(des => des.id == id).name} />

      <div className="form-contenedor">

        <div className="destination-card">
          <div className="destination-left">
            <div className="price-box">
              <span className="label">Precio desde Buenos Aires</span>
              <span className="price"><br />${Number(destinos.find(des => des.id == id).sample_price)?.toLocaleString('es-AR')}</span>
            </div>

            <p className="description">
              {destinos.find(des => des.id == id).description}
            </p>

            <div className="categories-box">
              <span className="label">Categorías</span>
              <div className="categories">
                {categorias.map((cat) => (
                  <NavLink key={cat.id} className="myLink category-tag" to={`/destinos?categorias=${cat.id}`}>
                    <span key={cat.id}>
                      {cat.name}
                    </span>
                  </NavLink>
                ))}
              </div>
            </div>

            <div className='button-fav-share'>
              <button
                className={`favorite-button ${isFavorite ? "active" : ""}`}
                onClick={() => toggleFavorite()}
                aria-label="Marcar como favorito"
              >
                <img
                  src={!isFavorite ? new URL(`../../assets/destinos/fav-icon-unactive.png`, import.meta.url).href :
                    new URL(`../../assets/destinos/fav-icon-active.png`, import.meta.url).href}
                  alt={isFavorite ? "Quitar de favoritos" : "Marcar como favorito"}
                  className="heart-img"
                />
              </button>

              <button
                className={`favorite-button`}
                onClick={() => setCompartir(true)}
                aria-label="compartir"
              >
                <img
                  src={new URL(`../../assets/destinos/compartir.png`, import.meta.url).href}
                  alt="compartir"
                  className="heart-img"
                />
              </button>
            </div>
            {favError &&
              <span className="form-error">Inicie sesión para agregar el destino a favoritos</span>}
          </div>

          <div className="destination-right">
            <div className="title-row">
              <h1>{destinos.find(des => des.id == id).name ? destinos.find(des => des.id == id).name : ""}</h1>
              <div className="rating">
                ⭐ <span>{promedioRating()?.toFixed(2)}</span>
              </div>
            </div>
            <div className="form-info form-item">
              <label>Seleccione un Origen para ver su disponibilidad </label>
            </div>
            <div >
              <select value={origen} onChange={e => setOrigen(Number(e.target.value))}>
                <option value="">Seleccione el Origen</option>
                {destinos.sort((a, b) => a.name.localeCompare(b.name))
                  .filter(d => d.id != id)
                  .map(d => (
                    <option key={d.id} value={d.id}>
                      {d.name}
                    </option>
                  ))}
              </select>
              <div className="calendar-grid">
                <Calendar
                  date={fechaIda}
                  onChange={setFechaIda}
                  minDate={new Date()}
                  disabledDay={disableAllExceptAllowedIda}
                  dayContentRenderer={reservaDatesIda}
                />
              </div>
            </div>

            <button className="action-button"
              onClick={() => user ? navigate(`/reservas?destino=${id}&origen=${origen}&ida=${fechaIda}`) : navigate("/login?error=reserva")}>
              Reservar mi vuelo
            </button>

          </div>
        </div>
        <DestinoImagenesComponent destino={destinos.find(des => des.id == id)} />
        <div className="form-info form-item">
          <label>¿Que oferece este Destino?: </label>
        </div>
        <div className='characteristics-container'>
          {destinos.find(des => des.id == id).characteristics.map(cha => (
            <div key={cha} className='char-item'>
              <span className="char-name">{caracteristicas && caracteristicas.find(caract => caract.id == cha)?.name}</span>
              <img className='char-icon'
                src={new URL(`../../assets/caracteristicas/${caracteristicas.find(caract => caract.id == cha)?.address
                  }.png`, import.meta.url).href}
                alt={cha} />
            </div>
          ))}
        </div>

        <div className="form-info form-item">
          <label>Reseñas totales: {reviewsPorDestino.length} </label>
        </div>

        <ReviewsComponent reviews={[...reviewsPorDestino].reverse()} />

        <PoliticasComponent politicas={destinos.find(des => des.id == id).policies} />

        {compartir &&
          <CompartirComponent destino={destinos.find(des => des.id == id)} url={"http://localhost:5173/destinoinfo/"} id={id} setCompartir={setCompartir} />
        }
      </div>
    </>

  )
}
