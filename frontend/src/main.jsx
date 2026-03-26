import {createRoot} from 'react-dom/client'
import {BrowserRouter, Route, Routes} from "react-router";
import App from './App.jsx'
import ContactSearchMain from "./ContactSearchMain.jsx";


createRoot(document.getElementById('root')).render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App />} />
            <Route path="/contacts" element={<ContactSearchMain/>} />
        </Routes>
    </BrowserRouter>,
)
