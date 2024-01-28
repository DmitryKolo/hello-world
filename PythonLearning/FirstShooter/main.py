import pygame
from settings import *
from player import Player
import math
from map import world_map
from ray_casting import ray_casting


def prime_num():
    nm = 2
    while True:
        sq = math.ceil(nm**1/2)
        for i in range(2, sq+1):
            if (nm % i) == 0:
                break
        yield nm
        nm += 1

for num in prime_num():
    print(num)

pygame.init()
sc = pygame.display.set_mode((WIDTH, HEIGHT))
clock = pygame.time.Clock()
player = Player()

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            exit()

    player.movement()
    sc.fill(BLACK)
    pygame.draw.rect(sc, BLUE, (0, 0, WIDTH, HALF_HEIGHT))
    pygame.draw.rect(sc, DARKGRAY, (0, HALF_HEIGHT, WIDTH, HALF_HEIGHT))

    ray_casting(sc,player.pos, player.angle)

    # pygame.draw.circle(sc, GREEN, player.pos, 12)
    # pygame.draw.line(sc, GREEN, player.pos,
    #                  (player.x + WIDTH * math.cos(player.angle),
    #                   player.y + WIDTH * math.sin(player.angle)))
    # for x, y in world_map:
    #     pygame.draw.rect(sc, DARKGRAY, (x, y, TILE, TILE), 2)

    pygame.display.flip()
    clock.tick(FPS)
