import { useState, useEffect, useContext } from "react"
import { NavLink } from "react-router-dom"
import { DestinosContext } from "../context/Destinos/DestinosContext"
import { format } from "date-fns"
import { DateRange } from "react-date-range"

export const DestinoFormComponent = () => {

    const { destinos, fetchDestinos } = useContext(DestinosContext)

    const [origen, setOrigen] = useState(0)
    const [destino, setDestino] = useState(0)
    const [seleccion, setSeleccion] = useState([{
        startDate: new Date(),
        endDate: new Date(),
        key: "selection"
    }])
    const [pasajeros, setPasajeros] = useState({
        economy: 0,
        premiumEconomy: 0,
        business: 0,
        first: 0
    })

    const pasajerosTitle = [
        "Clase Económica:",
        "Clase Económica Premium:",
        "Clase Ejecutiva:",
        "Primera Clase:"
    ]

    const [openCalendar, setOpenCalendar] = useState(false)
    const [openOrigen, setOpenOrigen] = useState(false)
    const [openDestino, setOpenDestino] = useState(false)
    const [openPasajero, setOpenPasajero] = useState(false)

    const esHoy = (fecha) => {
        const f = new Date(fecha);
        const hoy = new Date();

        f.setHours(0, 0, 0, 0);
        hoy.setHours(0, 0, 0, 0);

        return f.getTime() === hoy.getTime();
    }

    useEffect(() => {
        fetchDestinos()
    }, [])

    const handleDateChange = item => {
        setSeleccion([{ ...item.selection, key: "selection" }])
    }

    const handlePasajerosChange = (type, value) => {
        setPasajeros(prev => ({ ...prev, [type]: Math.max(0, Number(value)) }))
    }



    return (
        <div className='destinoform'>
            <div className="destinoform-selector">
                <div onClick={() => openOrigen ? setOpenOrigen(false) : setOpenOrigen(true)}><b>Origen</b>
                    <br /> {origen != 0 && destinos.find(d => d.id == origen).name}
                </div>

                {openOrigen && (
                    <ul className="custom-select-options-origen">
                        {destinos.sort((a, b) => a.name.localeCompare(b.name))
                            .filter(d => d.id !== destino)
                            .map(d => (
                                <li
                                    key={d.id}
                                    className="custom-select-option"
                                    onClick={() => {
                                        setOrigen(d.id);
                                        setOpenOrigen(false);
                                    }}
                                >
                                    {d.name}
                                </li>
                            ))}
                    </ul>
                )}

            </div>
            <span className='separador'></span>

            <div className="destinoform-selector">
                <div onClick={() => openDestino ? setOpenDestino(false) : setOpenDestino(true)}><b>Destino</b>
                    <br /> {destino != 0 && destinos.find(d => d.id == destino).name}
                </div>

                {openDestino && (
                    <ul className="custom-select-options-destino">
                        {destinos.sort((a, b) => a.name.localeCompare(b.name))
                            .filter(d => d.id !== origen)
                            .map(d => (
                                <li
                                    key={d.id}
                                    className="custom-select-option"
                                    onClick={() => {
                                        setDestino(d.id);
                                        setOpenDestino(false);
                                    }}
                                >
                                    {d.name}
                                </li>
                            ))}
                    </ul>
                )}

            </div>
            <span className='separador'></span>
            <div className="destinoform-selector" onClick={() => openCalendar ? setOpenCalendar(false) : setOpenCalendar(true)}>
                <b>Fecha de Ida</b>
                <br /> {!esHoy(seleccion[0].startDate) && `${format(seleccion[0].startDate, "dd/MM/yyyy")}`}
            </div>
            <span className='separador'></span>
            <div className="destinoform-selector" onClick={() => openCalendar ? setOpenCalendar(false) : setOpenCalendar(true)}>
                <b>Fecha de Vuelta</b>
                <br /> {!esHoy(seleccion[0].endDate) && `${format(seleccion[0].endDate, "dd/MM/yyyy")}`}
            </div>
            <span className='separador'></span>
            {openCalendar && (
                <div className="vuelos-calendar">
                    <DateRange
                        ranges={seleccion}
                        onChange={handleDateChange}
                        minDate={new Date()}
                        rangeColors={["#003a8f"]}
                    />
                </div>
            )}

            <div className="destinoform-selector">
                <div onClick={() => openPasajero ? setOpenPasajero(false) : setOpenPasajero(true)}><b>Pasajeros</b>
                    <br /> {!(pasajeros.economy == 0 &&
                        pasajeros.premiumEconomy == 0 &&
                        pasajeros.business == 0 &&
                        pasajeros.first == 0) &&
                        `${pasajeros.economy} | ${pasajeros.premiumEconomy} | ${pasajeros.business} | ${pasajeros.first}`}
                </div>

                {openPasajero &&
                    <div className="pasajeros-calendar">
                        {Object.keys(pasajeros).map((type, index) => (
                            <div className="pasajeros-row" key={type}>
                                <span>{pasajerosTitle[index]}</span>
                                <input
                                    type="number"
                                    min="0"
                                    value={pasajeros[type]}
                                    onChange={e => handlePasajerosChange(type, e.target.value)}
                                />
                            </div>
                        ))}
                    </div>}
            </div>

            <span className='separador'></span>
            <NavLink
                to={`/reservas?origen=${origen ?? ""}&destino=${destino ?? ""}&ida=${format(seleccion[0].startDate, "yyyy-MM-dd")}&vuelta=${format(seleccion[0].endDate, "yyyy-MM-dd")}&c1=${pasajeros.economy}&c2=${pasajeros.premiumEconomy}&c3=${pasajeros.business}&c4=${pasajeros.first}`}>
                <button className='buscar'> Buscar </button>
            </NavLink>

        </div>
    )
}
