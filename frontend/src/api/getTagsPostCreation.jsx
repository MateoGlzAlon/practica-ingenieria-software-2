import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getTagsPostCreation() {
    try {
        const response = await axios.get(`${DATA.apiURL}/tags/available`, {
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