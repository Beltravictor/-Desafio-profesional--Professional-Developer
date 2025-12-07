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

    const addCategoria = async (categoria, token) => {
        try {
            const res = await fetch("http://localhost:8081/categorias", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(categoria)
            })
            fetchCategorias()
            const data = await res.json()
        } catch (error) {
            console.error("Error al enviar POST:", error);
        }
    }

    const eliminarCategoria = async (id, token) => {
        try {
            const res = await fetch(`http://localhost:8081/categorias/${id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })

            if (!res.ok) {
                throw new Error("Error al eliminar")
            }
            fetchCategorias()
            console.log("Eliminado correctamente")
        } catch (error) {
            console.error("Error:", error)
        }
    }

    const editarCategoria = async (newCategoria, token) => {
        try {
            const res = await fetch("http://localhost:8081/categorias", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(newCategoria)
            })
            const data = await res.text()
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