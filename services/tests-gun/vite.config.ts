import { defineConfig } from 'vitest/config'

export default defineConfig({
  test: {
    open: false,
    uiBase: '/',
    ui: true
  },
  server: {
    host: '0.0.0.0',
  }
})