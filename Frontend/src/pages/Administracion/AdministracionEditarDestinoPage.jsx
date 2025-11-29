import { useContext, useEffect, useState } from 'react'
import { CategoriasContext } from "../../context/Categorias/CategoriasContext"
import '../../styles/AdministracionEditar.css'
import '../../styles/DestinosComponent.css'
import { DestinosContext } from '../../context/Destinos/DestinosContext'
import { NavLink, useParams } from 'react-router-dom'
import { HeaderComponent } from '../../components/HeaderComponent'
import { AdministracionNoResponsive } from '../../components/AdministracionNoResponsive'

export const AdministracionEditarDestinoPage = () => {

  const { id } = useParams()

  const { categorias, fetchCategorias } = useContext(CategoriasContext)
  const { destinoPorID, buscarDestinoPorId, editarDestinos } = useContext(DestinosContext)

  const [nombre, setNombre] = useState('')
  const [precio, setPrecio] = useState('')
  const [imagenes, setImagenes] = useState('')
  const [categoria, setCategoria] = useState([])
  const [rating, setRating] = useState('')
  const [descripcion, setDescripcion] = useState('')

  const [confirmacion, setConfirmacion] = useState(false)
  const [mensajeError, setMensajeError] = useState(false)
  const [mensajeErrorDescripcion, setMensajeErrorDescripcion] = useState('')

  useEffect(() => {
    buscarDestinoPorId(id)
    fetchCategorias()
  }, [])

  useEffect(() => {
    if (destinoPorID) {
      setNombre(destinoPorID?.name ?? "")
      setPrecio(destinoPorID?.sample_price ?? "")
      setImagenes(destinoPorID?.images?.join("\n") ?? "")
      setCategoria(destinoPorID?.categories ?? [])
      setRating(destinoPorID?.rating ?? "")
      setDescripcion(destinoPorID?.description ?? "")
    }
  }, [destinoPorID])

  const agregarCategoria = (e) => {
    if (e.target.value)
      setCategoria([...categoria, Number(e.target.value)])
  }

  const verificarCampos = () => {
    if (nombre && precio && imagenes && categoria.length && rating && descripcion) {
      editarDestino()
    } else {
      setMensajeErrorDescripcion('Error: Complete todos los campos')
      setMensajeError(true)
    }
  }

  const editarDestino = () => {

    const arrayImagenes = imagenes.split("\n")

    const newDestino = {
      id: id,
      name: nombre,
      sample_price: precio,
      images: arrayImagenes,
      categories: categoria,
      rating: rating,
      description: descripcion
    }
    setConfirmacion(true)
    editarDestinos(newDestino)
  }

  if (!destinoPorID || Object.keys(destinoPorID).length === 0) {
    return <p>Cargando destino...</p>
  }

  return (
    <>
      <AdministracionNoResponsive />
      <HeaderComponent name={destinoPorID.name} />

      <div className="form-contenedor">

        {
          confirmacion &&
          <div className="editar-form">
            <div className="editar-container">
              <h2 className="form-title">Destino Editado Con Éxito</h2>
              <div className="botones-editar">
                <button type="button" onClick={() => setConfirmacion(false)}>Guardar</button>
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
          <div className="form-item">
            <label>Precio</label>
            <input type="number" placeholder="Ingresa el Precio" value={precio}
              onChange={(e) => setPrecio(e.target.value)} />
          </div>

          <div className="form-item">
            <label>Rating</label>
            <input type="number" placeholder="Ingresa el Rating" value={Number(rating)}
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
          <button className="form-btn" onClick={() => verificarCampos()}>Editar Destino</button>
          <NavLink className="myLink" to={`/administracion/destinos`}>
            <button className="form-btn">Cancelar</button>
          </NavLink>
        </div>

      </div>
    </>

  )
}
