FROM node:12.19.0

WORKDIR ./

ENV PATH ./node_modules/.bin:$PATH

COPY package.json ./package.json
RUN npm install --silent
RUN npm install react-scripts@3.0.1 -g

COPY . .

CMD ["npm", "start"]
