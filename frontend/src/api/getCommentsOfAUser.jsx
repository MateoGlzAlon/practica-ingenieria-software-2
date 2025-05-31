import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getCommentsOfAUser(id) {
    try {

        const response = await axios.get(`${DATA.apiURL}/comments/user/${id}`, {
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