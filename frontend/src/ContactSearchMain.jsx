import React, {useEffect, useState} from 'react';
import ContactTable from './ContactTable';
import ContactSearch from "./ContactSearch.jsx";
import {Outlet, useNavigate, useParams, useSearchParams} from 'react-router';

function ContactSearchMain() {
    const [contacts, setContacts] = useState(null);
    const [searchInput, setSearchInput] = useState(null);

    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const search = searchParams.get("keyword") || "";
    const { id } = useParams();
    console.log(search)

    useEffect(() => {
        fetch(`/api/contacts?keyword=${search}`)
            .then(res => res.json())
            .then(setContacts);
    }, [search]);

    const handleEditClick = (contact) => {
        navigate(`/contacts/${contact.id}/edit`)
    };

    const handleClose = () => {
        navigate(-1);
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
            <ContactSearch setContacts={setContacts} searchInput={search} setSearchInput={setSearchInput}/>
            {contacts?.length > 0 ? (<ContactTable contacts={contacts} onDelete={handleDelete} onEdit={handleEditClick} />) : (<p id="search-result-msg">No Result</p>)}
            <Outlet context={{ contacts, handleSave, handleClose }} key={id}/>
        </>
    );
}

export default ContactSearchMain;