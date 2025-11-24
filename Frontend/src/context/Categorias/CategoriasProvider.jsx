import React, { useEffect, useState } from 'react'
import { CategoriasContext } from './CategoriasContext'

export const CategoriasProvider = ({ children }) => {

    const [categorias, setCategorias] = useState([])

    const fetchCategorias = async () => {
        try {
            const res = await fetch('http://localhost:8081/categorias')
            const data = await res.json()
            setCategorias(data)
        } catch (error) {
            console.error(error)
        }
    }

    const addCategoria = async (categoria) => {
        try {
            const res = await fetch("http://localhost:8081/categorias", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(categoria)
            });
            fetchCategorias()
            const data = await res.json()
            console.log("Respuesta:", data)

        } catch (error) {
            console.error("Error al enviar POST:", error);
        }
    }

    const eliminarCategoria = async (id) => {
        try {
            const res = await fetch(`http://localhost:8081/categorias/${id}`, {
                method: "DELETE"
            });

            if (!res.ok) {
                throw new Error("Error al eliminar")
            }
            fetchCategorias()
            console.log("Eliminado correctamente")
        } catch (error) {
            console.error("Error:", error)
        }
    }

    const editarCategoria = async (newCategoria) => {
        try {
            const res = await fetch("http://localhost:8081/categorias", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newCategoria)
            });
            const data = await res.text()
            console.log("Respuesta:", data)
        } catch (error) {
            console.error("Error al Actualizar:", error)
        }
    }

    const [categoriaPorID, setCategoriaPorID] = useState({})

    const buscarCategoriaPorId = async (id) => {
        try {
            const res = await fetch(`http://localhost:8081/categorias/${id}`)
            const data = await res.json()
            setCategoriaPorID(data)
        } catch (error) {
            console.error(error)
        }
    }


    return (
        <CategoriasContext value={{
            categorias, setCategorias, fetchCategorias,
            addCategoria, eliminarCategoria, editarCategoria,
            categoriaPorID, buscarCategoriaPorId
        }}>
            {children}
        </CategoriasContext>

    )
}