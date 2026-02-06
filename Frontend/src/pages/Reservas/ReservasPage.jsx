import { useContext, useEffect, useState } from "react"
import { Calendar } from "react-date-range"
import { format } from "date-fns"
import "react-date-range/dist/styles.css"
import "react-date-range/dist/theme/default.css"
import "../../styles/ReservasPage.css"
import { useNavigate, useSearchParams } from "react-router-dom"
import { DestinosContext } from "../../context/Destinos/DestinosContext"
import { AuthContext } from "../../context/Usuario/AuthProvider"
import { VuelosContext } from "../../context/Vuelos/VuelosContext"
import { UsuarioContext } from "../../context/Usuario/UsuarioContext"
import { MyUserContext } from "../../context/MyUser/MyUserContext"

export const ReservasPage = () => {
  const { user } = useContext(AuthContext)
  const { profile, getProfile } = useContext(UsuarioContext)
  const { crearReserva, misReservas, verMiReservas, } = useContext(MyUserContext)
  const navigate = useNavigate()

  const [params] = useSearchParams()

  const { destinos, fetchDestinos } = useContext(DestinosContext)
  const { vuelos, vuelosPorOrigenDestino } = useContext(VuelosContext)

  const [origen, setOrigen] = useState("")
  const [destino, setDestino] = useState("")

  const [fechaIda, setFechaIda] = useState(new Date())
  const [fechaVuelta, setFechaVuelta] = useState(new Date())

  const [toggleIdaVuelta, setToggleIdaVuelta] = useState(false)
  const [errors, setErrors] = useState([])
  const [confirmacion, setConfirmacion] = useState(false)
  const [reservaOk, setReservaOk] = useState(false)
  const [loadingVuelos, setLoadingVuelos] = useState(false)
  const [errorVuelos, setErrorVuelos] = useState(false)

  const [pasajeros, setPasajeros] = useState(({
    economy: 0,
    premiumEconomy: 0,
    business: 0,
    first: 0
  }))

  const pasajerosTitle = [
    "Clase Económica",
    "Clase Económica Premium",
    "Clase Ejecutiva",
    "Primera Clase"
  ]

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
    setOrigen(Number(params.get("origen")) || "0")
    setDestino(Number(params.get("destino")) || "0")

    setFechaIda(params.get("ida") ? new Date(params.get("ida")) : new Date())
    setFechaVuelta(params.get("vuelta") ? new Date(params.get("vuelta")) : new Date())

    setPasajeros({
      economy: Number(params.get("c1")) || 0,
      premiumEconomy: Number(params.get("c2")) || 0,
      business: Number(params.get("c3")) || 0,
      first: Number(params.get("c4")) || 0
    })

    setToggleIdaVuelta(params.get("vuelta") ? true : false)

    fetchDestinos()
  }, [])

  useEffect(() => {
    if (origen && destino && origen != 0 && destino != 0) {
      setLoadingVuelos(true)
      vuelosPorOrigenDestino(origen, destino)
    }
  }, [origen, destino])

  useEffect(() => {
    if (!loadingVuelos) return
    if (origen && destino && origen != 0 && destino != 0) {
      if (vuelos.length === 0)
        setErrorVuelos(true)
      else
        setErrorVuelos(false)
      setLoadingVuelos(false)
    }
  }, [vuelos])

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

  const disableAllExceptAllowedVuelta = (date) => {
    const allowedDates = new Set()
    vuelos.forEach((vuelo) => {
      allowedDates.add(normalizeToYMD(vuelo.return_date))
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

  const reservaDatesVuelta = (date) => {
    const returnFlight = misReservas.map(r => r.returnFlight)

    const misVuelos = vuelos.filter(v =>
      returnFlight.includes(v.id)
    )

    const allowedDates = new Set(
      misVuelos.map(v => normalizeToYMD(v.return_date))
    )
    const isMarked = allowedDates.has(normalizeToYMD(date))

    return (
      <span className={isMarked ? 'fecha-marcada' : ''}>
        {date.getDate()}
      </span>
    )
  }

  const handlePasajerosChange = (type, value) => {
    setPasajeros(prev => ({
      ...prev,
      [type]: Math.max(0, Number(value))
    }))
  }

  const toggleOrigenDestino = () => {
    const aux = origen
    setOrigen(destino)
    setDestino(aux)
  }

  const isDateAllowedIda = (date) => !disableAllExceptAllowedIda(date)

  const isDateAllowedVuelta = (date) => !disableAllExceptAllowedVuelta(date)

  const handleSubmit = e => {
    e.preventDefault()
    const validationErrors = []

    if (!user) {
      validationErrors.push("Debés iniciar sesión para realizar una reserva.")
    }

    const totalPasajeros =
      pasajeros.economy +
      pasajeros.premiumEconomy +
      pasajeros.business +
      pasajeros.first

    if (totalPasajeros < 1) {
      validationErrors.push("Debés seleccionar al menos un pasajero.")
    }

    if (!isDateAllowedIda(fechaIda)) {
      validationErrors.push("La fecha de ida no es válida para el vuelo seleccionado.")
    }

    if (toggleIdaVuelta) {
      if (!isDateAllowedVuelta(fechaVuelta)) {
        validationErrors.push("La fecha de vuelta no es válida para el vuelo seleccionado.")
      }

      if (fechaIda >= fechaVuelta) {
        validationErrors.push(
          "La fecha de ida debe ser anterior a la fecha de vuelta."
        )
      }
    }

    if (validationErrors.length > 0) {
      setErrors(validationErrors)
      return
    }

    setErrors([])
    setConfirmacion(true)
  }

  const reservarVuelo = () => {

    const vueloIda = vuelos.find(v =>
      normalizeToYMD(v.departure_date) == normalizeToYMD(fechaIda)
    )

    const vueloVuelta = toggleIdaVuelta ? (vuelos.find(v =>
      normalizeToYMD(v.return_date) == normalizeToYMD(fechaVuelta)
    )) : null

    const reserva = {
      economyClass: pasajeros.economy,
      premium_economyClass: pasajeros.premiumEconomy,
      businessClass: pasajeros.business,
      firstClass: pasajeros.first,
      totalPrice: 1000,
      departure_date: fechaIda,
      return_date: toggleIdaVuelta ? fechaVuelta : null,
      startFlight: vueloIda.id,
      returnFlight: toggleIdaVuelta ? vueloVuelta.id : null
    }

    crearReserva(user.token, reserva)
    setConfirmacion(false)
    verMiReservas(user.token)
    setReservaOk(true)
  }

  return (
    <div className="flight-search-page">
      <form className="flight-search-card" onSubmit={handleSubmit}>
        <h1>Reservá tu próximo vuelo</h1>

        <div className="search-grid">
          <div className="input-group">
            <label>Origen</label>
            <select value={origen} onChange={e => setOrigen(Number(e.target.value))}>
              <option value="">Seleccione el Origen</option>
              {destinos.sort((a, b) => a.name.localeCompare(b.name))
                .filter(d => d.id !== destino)
                .map(d => (
                  <option key={d.id} value={d.id}>
                    {d.name}
                  </option>
                ))}
            </select>
          </div>

          <button
            type="button"
            className="swap-button"
            onClick={toggleOrigenDestino}
            aria-label="Intercambiar origen y destino"
          >
            ⇄
          </button>

          <div className="input-group">
            <label>Destino</label>
            <select value={destino} onChange={e => setDestino(Number(e.target.value))}>
              <option value="">Seleccione el Destino</option>
              {destinos.sort((a, b) => a.name.localeCompare(b.name))
                .filter(d => d.id !== origen)
                .map(d => (
                  <option key={d.id} value={d.id}>
                    {d.name}
                  </option>
                ))}
            </select>
          </div>
        </div>
        <div className="date">
          <div className="trip-type-toggle">
            <button
              type="button"
              className={`trip-button ${!toggleIdaVuelta ? "active" : ""}`}
              onClick={() => setToggleIdaVuelta(false)}
            >
              Solo ida
            </button>

            <button
              type="button"
              className={`trip-button ${toggleIdaVuelta ? "active" : ""}`}
              onClick={() => setToggleIdaVuelta(true)}
            >
              Ida y vuelta
            </button>
          </div>
          {toggleIdaVuelta ?
            <div className="date-input">
              {`${format(fechaIda, "dd/MM/yyyy")} → ${format(
                fechaVuelta,
                "dd/MM/yyyy"
              )}`}
            </div> :
            <div className="date-input">
              {`${format(fechaIda, "dd/MM/yyyy")}`}
            </div>}
          {toggleIdaVuelta ?
            <div className="calendar-grid">
              <Calendar
                date={fechaIda}
                onChange={setFechaIda}
                minDate={new Date()}
                disabledDay={disableAllExceptAllowedIda}
                dayContentRenderer={reservaDatesIda}
              />
              <Calendar
                date={fechaVuelta}
                onChange={setFechaVuelta}
                minDate={fechaIda}
                disabledDay={disableAllExceptAllowedVuelta}
                dayContentRenderer={reservaDatesVuelta}
              />
            </div>
            :
            <div className="calendar-grid">
              <Calendar
                date={fechaIda}
                onChange={setFechaIda}
                minDate={new Date()}
                disabledDay={disableAllExceptAllowedIda}
                dayContentRenderer={reservaDatesIda}
              />
            </div>}
        </div>

        <div className="passenger-section">
          <h2>Pasajeros</h2>
          {Object.keys(pasajeros).map((type, index) => (
            <div className="passenger-row" key={type}>
              <span>{pasajerosTitle[index]}</span>
              <input
                type="number"
                min="0"
                value={pasajeros[type]}
                onChange={e => handlePasajerosChange(type, e.target.value)}
              />
            </div>
          ))}
        </div>
        {errors.length > 0 && (
          <div className="error-box">
            {errors.map((err, index) => (
              <p key={index} className="error">
                {err}
              </p>
            ))}
          </div>
        )}
        {user && profile ?
          <>
            <div className="calendar-grid">
              <section className="user-details-card">
                <h2 className="user-details-title">Datos del usuario</h2>

                <div className="user-details-row">
                  <span className="label">Nombre completo</span>
                  <span className="value">{profile.firstname}  {profile.lastname}</span>
                </div>

                <div className="user-details-row">
                  <span className="label">Email</span>
                  <span className="value">{profile.email}</span>
                </div>

                <div className="user-details-row">
                  <span className="label">Miembro desde</span>
                  <span className="value">
                    {new Date(profile.creationDate).toLocaleDateString("es-AR")}
                  </span>
                </div>

                <div className="user-details-row">
                  <span className="label">Reservas Totales</span>
                  <span className="value">{misReservas.length}</span>
                </div>
              </section>

            </div>


            <button type="submit" className="search-button" >Reservar vuelo</button>
          </>
          :
          <button className="search-button" onClick={() => navigate("/login?error=reserva")}>Inicie Sesión para realizar una reserva</button>
        }


      </form>

      {
        confirmacion &&
        <div className="editar-form">
          <div className="editar-container">
            <h2 className="form-title">{`¿Seguro que quiere hacer una reservar un vuelo de ${toggleIdaVuelta ? "ida y vuelta" : "ida"}
            desde ${destinos.find(d => d.id == origen).name} a ${destinos.find(d => d.id == destino).name}?`}</h2>
            <div className="botones-editar">
              <button className='edit-button' onClick={() => reservarVuelo()}>Realizar Reserva</button>
              <button className='delete-button' onClick={() => setConfirmacion(false)}>Cancelar</button>
            </div>
          </div>
        </div>
      }

      {
        reservaOk &&
        <div className="editar-form">
          <div className="editar-container">
            <h2 className="form-title">Reserva realizada con exito, puede ver sus reservas de vuelos desde su perfil</h2>
            <div className="botones-editar">
              <button className='edit-button' onClick={() => setReservaOk(false)}>Ok!</button>
              <button className='edit-button' onClick={() => navigate("/perfil")}>Ir al perfil</button>

            </div>
          </div>
        </div>
      }

      {
        errorVuelos &&
        <div className="editar-form">
          <div className="editar-container">
            <h2 className="form-title">No se encontraron vuelos disponibles para su viaje</h2>
            <div className="botones-editar">
              <button className='edit-button' onClick={() => setErrorVuelos(false)}>Ok</button>
            </div>
          </div>
        </div>
      }
    </div>
  )
}
