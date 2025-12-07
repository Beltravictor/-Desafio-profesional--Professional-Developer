import { useContext, useEffect, useState } from 'react'
import { CategoriasContext } from "../../../context/Categorias/CategoriasContext"
import '../../../styles/AdministracionEditar.css'
import '../../../styles/DestinosComponent.css'
import { DestinosContext } from '../../../context/Destinos/DestinosContext'
import { NavLink } from 'react-router-dom'
import { HeaderComponent } from '../../../components/HeaderComponent'
import { AdministracionNoResponsive } from '../../../components/AdministracionNoResponsive'
import { AuthContext } from '../../../context/Usuario/AuthProvider'
import { AdministracionAutorizacion } from '../../../components/AdministracionAutorizacion'
import { CaracteristicasContext } from '../../../context/Caracteristicas/CaracteristicasContext'

export const AdministracionGuardarDestinoPage = () => {
    const { user } = useContext(AuthContext)

    if (user?.role !== 'ROLE_ADMIN')
        return (<AdministracionAutorizacion />)

    const { categorias, fetchCategorias } = useContext(CategoriasContext)
    const { caracteristicas, fetchCaracteristicas } = useContext(CaracteristicasContext)
    const { addDestinos, destinoPorNombre, buscarDestinoPorNombre } = useContext(DestinosContext)

    const [nombre, setNombre] = useState('')
    const [precio, setPrecio] = useState('')
    const [imagenes, setImagenes] = useState('')
    const [categoria, setCategoria] = useState([])
    const [caracteristica, setCaracteristica] = useState([])
    const [rating, setRating] = useState('')
    const [descripcion, setDescripcion] = useState('')

    const [confirmacion, setConfirmacion] = useState(false)
    const [mensajeError, setMensajeError] = useState(false)
    const [mensajeErrorDescripcion, setMensajeErrorDescripcion] = useState('')

    const agregarCategoria = (e) => {
        if (e.target.value)
            setCategoria([...categoria, Number(e.target.value)])
    }

    const agregarCaracteristica = (e) => {
        if (e.target.value)
            setCaracteristica([...caracteristica, Number(e.target.value)])
    }

    useEffect(() => {
        fetchCategorias()
        fetchCaracteristicas()
    }, [])


    useEffect(() => {
        if (nombre != '') {
            if (Object.keys(destinoPorNombre).length === 0) {
                guardarDestino()
            } else {
                setMensajeErrorDescripcion('Error: Ya existe un Denstino con ese nombre')
                setMensajeError(true)
            }
        }
    }, [destinoPorNombre])


    const verificarCampos = () => {
        if (nombre && precio && imagenes && categoria.length && caracteristica.length && rating && descripcion) {
            buscarDestinoPorNombre(nombre)
        } else {
            setMensajeErrorDescripcion('Error: Complete todos los campos')
            setMensajeError(true)
        }
    }


    const guardarDestino = () => {

        const arrayImagenes = imagenes.split("\n")

        const newDestino = {
            name: nombre,
            sample_price: precio,
            images: arrayImagenes,
            categories: categoria,
            characteristics: caracteristica,
            rating: rating,
            description: descripcion
        }
        setConfirmacion(true)
        addDestinos(newDestino, user.token)
        setNombre('')
        setPrecio('')
        setImagenes('')
        setCategoria([])
        setCaracteristica([])
        setRating('')
        setDescripcion('')
    }

    return (

        <>
            <AdministracionNoResponsive />
            <HeaderComponent />
            <div className="form-contenedor">

                {
                    confirmacion &&
                    <div className="editar-form">
                        <div className="editar-container">
                            <h2 className="form-title">Destino Creado Con Éxito</h2>
                            <div className="botones-editar">
                                <button className="form-btn" type="button" onClick={() => setConfirmacion(false)}>Ok</button>
                            </div>
                        </div>
                    </div>
                }

                {
                    mensajeError &&
                    <div className="editar-form">
                        <div className="editar-container">
                            <h2 className="form-title">{mensajeErrorDescripcion}</h2>
                            <div className="botones-editar">
                                <button className="form-btn" type="button" onClick={() => setMensajeError(false)}>Ok</button>
                            </div>
                        </div>
                    </div>
                }


                <div className="form-grid">

                    <div className="form-item">
                        <label>Nombre</label>
                        <input type="text" placeholder="Ingresa el nombre" value={nombre}
                            onChange={(e) => setNombre(e.target.value)} />
                    </div>

                    <div className="form-cat form-item">
                        <label>Categorías</label>
                        {categoria.map((cat) => (
                            <div key={cat} className="cat-linea">
                                <p key={cat}>{categorias.find(cate => cate.id === cat).name}</p>
                                <button className="btn-eliminar"
                                    onClick={() => setCategoria(categoria.filter(cate => cate !== cat))}
                                >X</button>
                            </div>
                        ))}

                        <select value="" onChange={(e) => agregarCategoria(e)}>
                            <option value="">Agregar Categoría</option>
                            {categorias.filter((cat) =>
                                !categoria.includes(cat.id)
                            ).map((cat, index) => (
                                <option key={index} value={cat.id}>{cat.name}</option>
                            ))}
                        </select>

                    </div>

                    <div className="form-carac form-item">
                        <label>Características</label>
                        {caracteristica.map((cat) => (
                            <div key={cat} className="cat-linea">
                                <p key={cat}>{caracteristicas.find(cate => cate.id === cat).name}</p>
                                <button className="btn-eliminar"
                                    onClick={() => setCaracteristica(caracteristica.filter(cate => cate !== cat))}
                                >X</button>
                            </div>
                        ))}

                        <select value="" onChange={(e) => agregarCaracteristica(e)}>
                            <option value="">Agregar Carcterística</option>
                            {caracteristicas.filter((cat) =>
                                !caracteristica.includes(cat.id)
                            ).map((cat, index) => (
                                <option key={index} value={cat.id}>{cat.name}</option>
                            ))}
                        </select>

                    </div>
                    <div className="form-item">
                        <label>Precio</label>
                        <input type="number" placeholder="Ingresa el Precio" value={precio}
                            onChange={(e) => setPrecio(e.target.value)} />
                    </div>

                    <div className="form-item">
                        <label>Rating</label>
                        <input type="number" placeholder="Ingresa el Rating" value={rating}
                            onChange={(e) => setRating(e.target.value)} />
                    </div>

                    <div className="form-item form-descripcion">
                        <label>Descripción</label>
                        <textarea placeholder="Ingresa la descripción" value={descripcion}
                            onChange={(e) => setDescripcion(e.target.value)} />
                    </div>

                    <div className="form-item form-descripcion">
                        <label>Imagenes (separadas por salto de línea)</label>
                        <textarea placeholder="Ingresa las imagenes" value={imagenes}
                            onChange={(e) => setImagenes(e.target.value)} />
                    </div>

                </div>
                <div className="form-contbtn">
                    <button className="form-btn" onClick={() => verificarCampos()}>Crear Destino</button>
                    <NavLink className="myLink" to={`/administracion/destinos`}>
                        <button className="form-btn">Cancelar</button>
                    </NavLink>
                </div>

            </div>
        </>

    )
}
