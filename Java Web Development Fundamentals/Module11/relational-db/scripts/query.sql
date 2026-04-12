SELECT S.STUDIO_NAME , m.*
FROM studios s JOIN movies m ON S.STUDIO_ID = m.STUDIO_ID
WHERE m.genre like '%Adventure%';
