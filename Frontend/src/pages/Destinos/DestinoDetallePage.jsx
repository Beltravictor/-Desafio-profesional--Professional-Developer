import React, { useContext, useEffect } from 'react'
import { DestinosContext } from '../../context/Destinos/DestinosContext'
import { CategoriasContext } from '../../context/Categorias/CategoriasContext'
import { NavLink, useParams } from 'react-router-dom'
import { HeaderComponent } from '../../components/HeaderComponent'

export const DestinoDetallePage = () => {
  const { id } = useParams()

  const { destinoPorID, buscarDestinoPorId } = useContext(DestinosContext)
  const { categorias, fetchCategorias } = useContext(CategoriasContext)

  useEffect(() => {
    buscarDestinoPorId(id)
    fetchCategorias()
  }, [])

  if (!destinoPorID || Object.keys(destinoPorID).length === 0 || !categorias) {
    return <p>Cargando destino...</p>
  }

  return (
    <>
      <HeaderComponent name={destinoPorID.name} />

      <div className="form-contenedor">

        <div className="form-grid">

          <div className="form-cat form-item">
            <label>Categorías</label>
            {destinoPorID.categories.map((cat) => (
              <div key={cat} className="cat-linea">
                <p key={cat}>{categorias.find(cate => cate.id === cat).name}</p>
              </div>
            ))}
          </div>
          <div className="form-info form-item">
            <label>Precio desde Buenos Aires</label>
            <div className="cat-linea">
              <p>${Number(destinoPorID.sample_price).toLocaleString('es-AR')}</p>
            </div>
          </div>

          <div className="form-info form-item">
            <label>Rating⭐</label>
            <div className="cat-linea">
              <p>{destinoPorID.rating}</p>
            </div>
          </div>

          <div className="form-info form-item">
            <label>Descripción</label>
            <div className="cat-linea">
              <p>{destinoPorID.description}</p>
            </div>
          </div>

          <div className='form-img'>
            <NavLink className="myLink" to={`/destinoinfo/imagenes/${destinoPorID.id}`}>
              <img src={destinoPorID.images[0]} alt={destinoPorID.name} />
            </NavLink>
          </div>

        </div>
      </div>
    </>

  )
}
