-- PK name
CREATE TABLE Users(
    name TEXT PRIMARY KEY,      -- Namn som PK kan ändras vid behov
    login TEXT UNIQUE
);

-- PK sender && message_date (kan ändras vid behov)
CREATE TABLE Messages(
    sender TEXT NOT NULL REFERENCES Users(name),
    message_date TIMESTAMPTZ NOT NULL DEFAULT now(),        -- date+time default till när en insert skapas i tabellen
    message_id INTEGER PRIMARY KEY, 
    FOREIGN KEY (sender) REFERENCES Users(name),        
    msg_type TEXT NOT NULL, 
    CHECK (msg_type IN ('text', 'image')),
    text_body TEXT,
    image_url TEXT,
    -- Antingen består ett meddelande av text, eller en image, inte både och samt inte inga av dem
    CONSTRAINT msg_type_check CHECK(
        (msg_type = 'text' 
        AND text_body IS NOT NULL
        AND image_url IS NULL)
        OR
        (msg_type = 'image'
        AND image_url IS NOT NULL
        AND text_body IS NULL)
    )
);

CREATE TABLE Chats(
    chat_id SERIAL PRIMARY KEY,
    chat_type TEXT NOT NULL CHECK (chat_type IN ('dm','group')),
    title TEXT, -- If group chat, can have title, otherwise if dm, null
    created_at TIMESTAMPTZ NOT NULL DEFAULT now() -- Kan vara bra att ha kanske? Annars kan ta bort
)

CREATE TABLE Chat_Members(
    chat_id INTEGER NOT NULL REFERENCES Chats(chat_id) ON DELETE CASCADE ,  -- Om en chat raderas, ta bort alla rader i denna tabell som har med den chatten att göra
    user_name TEXT NOT NULL REFERENCES Users(name) ON DELETE CASCADE,       -- Samma för en användare
    role TEXT NOT NULL DEFAULT 'member',
    joined_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY(chat_id, user_name)
)