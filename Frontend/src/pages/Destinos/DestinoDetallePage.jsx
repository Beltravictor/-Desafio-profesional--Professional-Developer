import { useContext, useEffect } from 'react'
import { DestinosContext } from '../../context/Destinos/DestinosContext'
import { CategoriasContext } from '../../context/Categorias/CategoriasContext'
import { useParams } from 'react-router-dom'
import { HeaderComponent } from '../../components/HeaderComponent'
import { DestinoImagenesComponent } from '../../components/DestinoImagenesComponent'
import { CaracteristicasContext } from '../../context/Caracteristicas/CaracteristicasContext'

export const DestinoDetallePage = () => {
  const { id } = useParams()

  const { destinoPorID, buscarDestinoPorId } = useContext(DestinosContext)
  const { categorias, fetchCategorias } = useContext(CategoriasContext)
  const { caracteristicas, fetchCaracteristicas } = useContext(CaracteristicasContext)

  useEffect(() => {
    buscarDestinoPorId(id)
    fetchCaracteristicas()
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
            <label>Categorías</label>
            {destinoPorID.categories.map((cat) => (
              <div key={cat} className="cat-linea">
                <p key={cat}>{categorias.find(cate => cate.id === cat).name}</p>
              </div>
            ))}
          </div>

          <div className="form-info form-item">
            <label>Descripción</label>
            <div className="cat-linea">
              <p>{destinoPorID.description}</p>
            </div>
          </div>

        </div>
        <div className="form-info form-item">
          <label>Fotos: </label>
        </div>
        <DestinoImagenesComponent destino={destinoPorID} />

        <div className="form-info form-item">
          <label>¿Que oferece este Destino?: </label>
        </div>
        <div className='characteristics-container'>
          {destinoPorID.characteristics.map(cha => (
            <div className='char-item'>
              <span className="char-name">{caracteristicas.find(caract => caract.id === cha).name}</span>
              <img className='char-icon'
                src={new URL(`../../assets/caracteristicas/${caracteristicas.find(caract => caract.id === cha).address
                  }.png`, import.meta.url).href}
                alt={cha} key={cha} />
            </div>
          ))}


        </div>
      </div>
    </>

  )
}
