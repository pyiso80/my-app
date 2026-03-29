import {createRoot} from 'react-dom/client'
import {BrowserRouter, Route, Routes} from "react-router";
import App from './App.jsx'
import ContactSearchMain from "./ContactSearchMain.jsx";
import ContactEditModal from "./ContactEditModal.jsx";


createRoot(document.getElementById('root')).render(
    <BrowserRouter>
        <Routes>
            <Route path="/contacts/new" element={<App />} />
            <Route path="/contacts" element={<ContactSearchMain/>}>
                <Route path=":id/edit" element={<ContactEditModal />} />
            </Route>
        </Routes>
    </BrowserRouter>,
)
