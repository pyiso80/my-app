import React, { useState } from 'react';

function ContactForm({ onSubmit }) {
    const [name, setName] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(name);   // delegate to parent
        setName('');      // optional: clear input
    };

    return (
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
    );
}

export default ContactForm;