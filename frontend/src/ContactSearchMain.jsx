import React, {useState} from 'react';
import ContactTable from './ContactTable';
import ContactSearch from "./ContactSearch.jsx";

function ContactSearchMain() {
    const [contacts, setContacts] = useState(null);
    const handleDelete = async (id) => {
        try {
            window.confirm("Are you sure to delete?")
            const response = await fetch(`/contacts/${id}`, {
                method: 'DELETE',
            });

            if (!response.ok) {
                throw new Error('Failed to delete');
            }

            console.log(`Deleted contact ${id}`);
        } catch (error) {
            console.error(error);
        }
    };
    if (contacts === null) {
        return (<ContactSearch setContacts={setContacts}/>)
    }
    return (
        <>
            <ContactSearch setContacts={setContacts}/>
            {contacts?.length > 0 ? (<ContactTable contacts={contacts} onDelete={handleDelete} />) : (<p id="search-result-msg">No Result</p>)}
        </>
    );
}

export default ContactSearchMain;