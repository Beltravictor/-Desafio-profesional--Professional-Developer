import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import prerender from "vite-plugin-prerenderer";
import destinos from "./src/data/destinos.json";

export default defineConfig({
  plugins: [
    react(),
    prerender({
      routes: [
        "/",
        ...destinos.map(d => `/destino/${d.id}`)
      ],
      renderer: "react",
    }),
  ],
});
