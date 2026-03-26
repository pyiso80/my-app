import React, { useState } from 'react';
import ContactTable from './ContactTable';
import ContactSearch from "./ContactSearch.jsx";

function ContactSearchMain() {
    const [contacts, setContacts] = useState([]);
    return (
        <>
            <ContactSearch setContacts={setContacts} />
            <ContactTable contacts={contacts} />
        </>
    );
}

export default ContactSearchMain;