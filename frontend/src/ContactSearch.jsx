import React, {useRef} from 'react';
import { useNavigate } from 'react-router';


function ContactSearch({ setContacts }) {
    const searchInputRef = useRef(null)
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const keyword = searchInputRef.current.value;

        try {
            navigate(`/contacts?keyword=${encodeURIComponent(keyword)}`)
            const response = await fetch(
                `/api/contacts?keyword=${encodeURIComponent(keyword)}`,
            );

            const data = await response.json();
            setContacts(data);
        } catch (err) {
            console.error("Error fetching contacts:", err);
        }
    }
    return (
        <form onSubmit={handleSubmit}>
            <div>
                <input type="text" name="keyword" ref={searchInputRef} />
            </div>
            <div>
                <button type="submit">Search</button>
            </div>
        </form>
    );
}

export default ContactSearch;