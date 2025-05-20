import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getAllTags(id) {
    try {
        const response = await axios.get(`${DATA.apiURL}/posts/details/${id}`, {
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