import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getProfileUser(id) {
    try {
        const response = await axios.get(`${DATA.apiURL}/users/profile/${id}`, {
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