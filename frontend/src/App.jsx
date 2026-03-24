import React, { useState } from 'react';
import ContactForm from './ContactForm';
import ContactTable from './ContactTable';

function App() {
    const [contacts, setContacts] = useState([]);
    return (
        <>
            <ContactForm setContacts={setContacts} />
            <ContactTable contacts={contacts} />
        </>
    );
}

export default App;