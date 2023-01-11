FROM node:16-alpine as build-step

COPY ./frontend/package.json ./
COPY ./frontend/src ./src
COPY ./frontend/public ./public
COPY ./frontend/tailwind.config.js ./tailwind.config.js
RUN npm install

CMD ["npm", "start"]
EXPOSE 3000