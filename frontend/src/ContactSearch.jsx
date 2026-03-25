import React, {useRef} from 'react';


function ContactSearch() {
    const searchInputRef = useRef(null)
    const handleSubmit = async (e) => {
        e.preventDefault();
        const keyword = searchInputRef.current.value;

        try {
            const response = await fetch(
                `/api/contacts?keyword=${encodeURIComponent(keyword)}`
            );

            const data = await response.json();
        } catch (err) {
            console.error("Error fetching contacts:", err);
        }
    }
    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Search:</label><br />
                <input type="text" name="keyword" ref={searchInputRef} />
            </div>
        </form>
    );
}

export default ContactSearch;