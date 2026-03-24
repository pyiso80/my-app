import React, { useState } from 'react';
import ContactForm from './ContactForm';
import ContactTable from './ContactTable';

function App() {
    const [contacts, setContacts] = useState([]);

    const handleAddContact = async (name) => {
        const payload = { name };

        try {
            const response = await fetch('/api', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload),
            });

            const data = await response.json();
            console.log(data)
            setContacts(data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <>
            <ContactForm onSubmit={handleAddContact} />
            <ContactTable contacts={contacts} />
        </>
    );
}

export default App;