import React, {useState} from 'react';
import ContactTable from './ContactTable';
import ContactSearch from "./ContactSearch.jsx";
import ContactEditModal from "./ContactEditModal.jsx";
import { useNavigate } from 'react-router';

function ContactSearchMain() {
    const [contacts, setContacts] = useState(null);
    const [editingContact, setEditingContact] = useState(null);
    const navigate = useNavigate();

    const handleEditClick = (contact) => {
        navigate(`/contacts/${contact.id}/edit`)
        setEditingContact(contact);
    };

    const handleClose = () => {
        setEditingContact(null);
    };

    const handleSave = async (updatedContact) => {

        const response = await fetch(`/api/contacts/${updatedContact.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedContact),
        });

        const updatedFromServer = await response.json();

        const updatedContacts = contacts.map(c =>
            c.id === updatedFromServer.id ? updatedFromServer : c
        );
        setContacts(updatedContacts)
        handleClose();
    };

    const handleDelete = async (id) => {
        try {
            window.confirm("Are you sure to delete?")
            const response = await fetch(`/api/contacts/${id}`, {
                method: 'DELETE',
            });

            if (!response.ok) {
                throw new Error('Failed to delete');
            }

            console.log(`Deleted contact ${id}`);
            setContacts(contacts.filter((it) => it.id !== id))
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
            {contacts?.length > 0 ? (<ContactTable contacts={contacts} onDelete={handleDelete} onEdit={handleEditClick} />) : (<p id="search-result-msg">No Result</p>)}
            {editingContact && (
                <ContactEditModal
                    contact={editingContact}
                    onClose={handleClose}
                    onSave={handleSave}
                />
            )}
        </>
    );
}

export default ContactSearchMain;