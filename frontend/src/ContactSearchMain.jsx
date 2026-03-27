import React, {useState} from 'react';
import ContactTable from './ContactTable';
import ContactSearch from "./ContactSearch.jsx";

function ContactSearchMain() {
    const [contacts, setContacts] = useState(null);
    if (contacts === null) {
        return (<ContactSearch setContacts={setContacts}/>)
    }
    return (
        <>
            <ContactSearch setContacts={setContacts}/>
            {contacts?.length > 0 ? (<ContactTable contacts={contacts}/>) : (<p id="search-result-msg">No Result</p>)}
        </>
    );
}

export default ContactSearchMain;