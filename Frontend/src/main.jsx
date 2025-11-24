import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { VuelosApp } from './VuelosApp'
import { BrowserRouter } from 'react-router-dom'



createRoot(document.getElementById('root')).render(
  <BrowserRouter>
    <StrictMode>
      <VuelosApp />
    </StrictMode>
  </BrowserRouter>

)
