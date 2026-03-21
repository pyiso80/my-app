import React, { useState } from 'react';

function App() {

    const [name, setName] = useState('');
    const [contacts, setContacts] = useState([]);

    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevents the browser from reloading

        const payload = { name: name };

        try {
            const response = await fetch('/api', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload), // Converts {name: "..."} to "{"name":"..."}"
            });

            const data = await response.json();
            setContacts(data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <input
                    id="name-input"
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="Enter name"
                />
                <button type="submit">Submit</button>
            </form>
            <table id="contact-table">
                <tbody>
                {contacts.map(contact => (
                    <tr><td key={contact.id}>{contact.name}</td></tr>
                ))}
                </tbody>
            </table>
        </>
    )
}

export default App
