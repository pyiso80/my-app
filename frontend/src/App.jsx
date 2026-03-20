import React, { useState } from 'react';

function App() {

    const [name, setName] = useState('');
    const [result, setResult] = useState(null);

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
            setResult(data);
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
            {result && (
                <p id="result-text">
                    Returned name: <strong>{result.name}</strong>
                </p>
            )}
        </>
    )
}

export default App
