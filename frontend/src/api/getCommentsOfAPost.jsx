import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getCommentsOfAPost(id, sort = 'newest') {

    try {
        const response = await axios.get(`${DATA.apiURL}/comments/post/${id}?sort=${sort}`, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.data;
    } catch (error) {
        console.error("Error fetching community stats:", error);
        throw error;
    }
}