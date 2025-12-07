import { useContext } from "react"
import { AuthContext } from "../context/Usuario/AuthProvider"

export const AvatarPerfilComponent = ({ className, onClick }) => {

    const { user } = useContext(AuthContext)
    return (
        <div 
            className={` ${className || ""}`}
            onClick={onClick}
        >{user?.name?.charAt(0)?.toUpperCase()}
            {user?.lastname?.charAt(0)?.toUpperCase()}</div>
    )
}
