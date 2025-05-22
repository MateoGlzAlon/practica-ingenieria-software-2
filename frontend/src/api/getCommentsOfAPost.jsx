import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getCommentsOfAPost(id) {
    try {
        const response = await axios.get(`${DATA.apiURL}/comments/post/${id}`, {
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