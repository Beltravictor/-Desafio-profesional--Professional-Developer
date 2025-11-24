import { useContext, useState } from "react";
import { DestinosContext } from "../context/Destinos/DestinosContext";

export const CrearDestinoComponent = () => {

    const { addDestinos } = useContext(DestinosContext)

    const admin = true

    const [editar, setEditar] = useState(false)

    const [nombre, setNombre] = useState('')
    const [precio, setPrecio] = useState('')
    const [imagenes, setImagenes] = useState([''])
    const [addImagenes, setAddImagenes] = useState('')
    const [categoria, setCategoria] = useState([''])
    const [addCategoria, setAddCategoria] = useState('')
    const [descripcion, setDescripcion] = useState('')

    const actualizarImagen = (i, valor) => {
        setImagenes(imgs => {
            const copia = [...imgs]
            copia[i] = valor
            return copia
        });
    };

    const actualizarCategoria = (i, valor) => {
        setCategoria(cats => {
            const copia = [...cats]
            copia[i] = valor
            return copia
        });
    };

    const guardarDestinos = () => {

        let imagensFinales = imagenes.filter(img => img !== '')
        if(addImagenes != ''){
            imagensFinales = [...imagensFinales, addImagenes]
            setAddImagenes('')
        }
        
        let categoriasFinales = categoria.filter(cat => cat !== '')
        
        if (addCategoria != '') {
            categoriasFinales = [...categoriasFinales, addCategoria]
            setAddCategoria('')
        }
        
        const newDestinos = {
            name: nombre,
            images: imagensFinales,
            categories: categoriasFinales,
            description: descripcion,
            sample_price: precio
        }
        addDestinos(newDestinos)
        cancelarDestinos()
    }

    const cancelarDestinos = () => {
        setEditar(false)
        setNombre('')
        setPrecio('')
        setImagenes([''])
        setAddImagenes('')
        setCategoria([''])
        setAddCategoria('')
        setDescripcion('')
    }


    return (
        <>
            <div>
                {admin && (
                    <div className="botones-vertical">
                        <button onClick={() => setEditar(true)} >Crear Destino</button>
                    </div>
                )}

                {editar && (
                    <div className="editar-form">
                        <div className="editar-container">
                            <h2 className="form-title">Editar elemento</h2>

                            <form >
                                <div className="campo">
                                    <label>Nombre: </label>
                                    <input type="text" value={nombre}
                                        onChange={(e) => setNombre(e.target.value)} />
                                </div>

                                <div className="campo">
                                    <label>Precio: </label>
                                    <input type="number" value={precio}
                                        onChange={(e) => setPrecio(e.target.value)} />
                                </div>

                                <div className="campo">
                                    <label>Imagenes: </label>
                                    <div>
                                        {imagenes.map((img, index) => (
                                            <input type="text" value={img}
                                                onChange={(e) => actualizarImagen(index, e.target.value)}
                                            />
                                        ))}
                                    </div>
                                </div>
                                <div className="campo">
                                    <label>Anadir Imagen: </label>
                                    <input type="text"
                                        onChange={(e) => setAddImagenes(e.target.value)} />
                                </div>

                                <div className="campo">
                                    <label>Categorias: </label>
                                    <div>
                                        {categoria.map((cat, index) => (
                                            <input type="number" value={cat}
                                                onChange={(e) => actualizarCategoria(index, e.target.value)}
                                            />
                                        ))}
                                    </div>
                                </div>
                                <div className="campo">
                                    <label>Anadir Categoria: </label>
                                    <input type="number"
                                        onChange={(e) => setAddCategoria(e.target.value)} />
                                </div>

                                <div className="campo">
                                    <label>Descripción: </label>
                                    <input type="text" value={descripcion}
                                        onChange={(e) => setDescripcion(e.target.value)} />
                                </div>

                                <div className="botones-editar">
                                    <button type="button" onClick={() => guardarDestinos()}>Crear Destino</button>
                                    <button type="button" onClick={() => cancelarDestinos()}>Cancelar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                )}
            </div>
        </>
    )
}
